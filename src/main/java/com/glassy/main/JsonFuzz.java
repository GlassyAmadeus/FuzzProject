package com.glassy.main;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class JsonFuzz {
    public static void main(String[] args) {
        String demo = "{\"num\":666,\"content\":\"test\"}";
        jsonFuzz(demo);
    }

    public static void jsonFuzz(String demo) {
        CheckFunc func = new CheckFunc() {
            @Override
            public void check(String origin, String fuzzData, char fuzzChar) {
                //由于打不风安全产品都会对字符串做trim处理，因此，如果fuzz的字符和原字符trim结果相同，基本没什么意义
                if (!origin.trim().equals(fuzzData.trim())) {
                    try {
                        Entity entity = JSONObject.parseObject(fuzzData, Entity.class);
                        //********该测试用例中主要用来发掘fastjson兼容而gson不兼容的特性，但有些安全产品使用自研json解析器，json兼容性更差，则可以注释掉下面代码，直接fuzz出fastjson的全部特性
                        try {
                            Gson gson = new Gson();
                            Entity gsonEntity = gson.fromJson(fuzzData, Entity.class);
                            if (gsonEntity.toString().equals("Entity{num=666, content='test'}")) {
                                //如果gson能解了，就代表这个特性gson也是可以兼容的，说明这个fuzzData是无效数据，因为大家都能解，安全产品就具备对这种payload的防御能力了，所以直接return
                                return;
                            }
                        } catch (Exception ignored) {
                            //如果gson报错了，我们直接忽略它，让程序继续往下走，因为我们期待的数据就是fastjson能解，而gson解不出来的数据
                        }
                        //**************************gson-end***************
                        if (entity.toString().equals("Entity{num=666, content='test'}")) {
                            System.out.println("charNum: " + (int) fuzzChar + "|char: " + fuzzChar + "|content: " + fuzzData);
                        }
                    } catch (Exception exception) {
                        if (exception instanceof JSONException) {

                        } else {
                            exception.printStackTrace();
                        }
                    }
                }
            }
        };
        int length = demo.length();
        for (int i = 0; i < length; i++) {
            System.out.println("*************************插入字符位置：" + i + "*************************");
            Utils.doFuzz(demo, i, HandleType.INSERT, func);
        }
        for (int i = 0; i < length; i++) {
            System.out.println("*************************替换字符位置：" + i + "*************************");
            Utils.doFuzz(demo, i, HandleType.REPLACE, func);
        }
    }
}

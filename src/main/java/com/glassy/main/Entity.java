package com.glassy.main;

public class Entity {
    private int num;
    private String content;

    public Entity() {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "num=" + num +
                ", content='" + content + '\'' +
                '}';
    }
}

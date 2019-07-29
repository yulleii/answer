package com.nowcoder.answer.async;
//标注每一个事件的类型
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
    EventType(int value){
        this.value=value;
    }
    public int getValue(){return value;}
}

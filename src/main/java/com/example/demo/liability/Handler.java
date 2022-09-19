package com.example.demo.liability;


/**
 * @create: 2022-09
 **/
public abstract class Handler<T> {
    public Handler handler;

    private void next(Handler handler){
        this.handler = handler;
    }

    public abstract void doHandler(Person person);

    public static class Builder<T>{
        private Handler<T> head;
        private Handler<T> tail;

        public Builder<T> addHandler(Handler<T> handler){
            if (this.head == null){
                this.head = this.tail = handler;
            }
            this.tail.next(handler);
            this.tail = handler;
            return this;
        }

        public Handler<T> check(){
            return this.head;
        }
    }
}

package com.taiji.base.sample.vo;

public class Hello {

    private String content;

    public Hello(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public static class HelloMessage {

        public HelloMessage(){

        }

		private String name;

        private String message;

		public String getName() {
			return name;
		}

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

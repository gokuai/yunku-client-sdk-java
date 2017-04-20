package com.gokuai.demo.other;

/**
 * Created by Brandon on 2017/4/19.
 */
public class OtherAPI {
    public static void main(String[] args) {
        YunkuOtherApiEngine.getInstance().loginSync("[account]", "[password]");
        YunkuOtherApiEngine.getInstance().otherApiRequest("[param1]", "[param2]");

    }
}

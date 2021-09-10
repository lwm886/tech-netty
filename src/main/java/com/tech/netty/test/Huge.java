package com.tech.netty.test;

/**
 * @author lw
 * @since 2021/9/8
 */
public class Huge implements Star {
    @Override
    public void sing(String song) {
        System.out.println("胡歌演唱"+song);
    }

    @Override
    public String act(String display) {
        System.out.println("胡歌出演电视剧 "+display);
        return "胡歌出演电视剧 "+display;
    }
}

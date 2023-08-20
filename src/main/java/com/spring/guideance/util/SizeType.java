package com.spring.guideance.util;

public enum SizeType { // 페이징
    SMALL(SIZE.SMALL),
    MEDIUM(SIZE.MEDIUM),
    LARGE(SIZE.LARGE);

    private final int size;

    SizeType(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static class SIZE {
        public static final int SMALL = 10;
        public static final int MEDIUM = 20;
        public static final int LARGE = 30;
    }
}
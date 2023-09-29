package com.spring.guideance.util;

public enum SizeUtil { // 페이징 size 및 Batch size 설정
    SMALL(SIZE.SMALL),
    MEDIUM(SIZE.MEDIUM),
    LARGE(SIZE.LARGE),

    PAGE_SMALL(SIZE.PAGE_SMALL),
    PAGE_MEDIUM(SIZE.PAGE_MEDIUM);

    private final int size;

    SizeUtil(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static class SIZE {
        public static final int SMALL = 100;
        public static final int MEDIUM = 200;
        public static final int LARGE = 500;
        public static final int PAGE_SMALL = 10;
        public static final int PAGE_MEDIUM = 20;
    }
}

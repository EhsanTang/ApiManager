package cn.crap.utils;

/**
 * @author Ehsan
 * @date 17/12/30 01:10
 */
public interface IAuthCode {
    /**
     * only myself can operate
     */
    int MY_DATE = -1;
    int VIEW = 100;
    int MOD_INTER = 1;
    int ADD_INTER = 2;
    int DEL_INTER = 3;

    int MOD_MODULE = 4;
    int ADD_MODULE = 5;
    int DEL_MODULE = 6;

    int MOD_ARTICLE = 7;
    int ADD_ARTICLE = 8;
    int DEL_ARTICLE = 9;

    int MOD_DICT = 10;
    int ADD_DICT = 11;
    int DEL_DICT = 12;

    int MOD_SOURCE = 13;
    int ADD_SOURCE = 14;
    int DEL_SOURCE = 15;

    int MOD_ERROR = 16;
    int ADD_ERROR = 17;
    int DEL_ERROR = 18;
}

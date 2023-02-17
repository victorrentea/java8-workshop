package victor.training.java.stream.yatzi;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class YatzyTest {

    @Test
    void chance_scores_sum_of_all_dice() {
        int expected = 15;
        int actual = Yatzy.chance(2,3,4,5,1);
        assertThat(actual).isEqualTo(expected);
        assertThat(Yatzy.chance(3, 3, 4, 5, 1)).isEqualTo(16);
    }

    @Test
    void yatzy_scores_50() {
        int expected = 50;
        int actual = Yatzy.yatzy(4,4,4,4,4);
        assertThat(actual).isEqualTo(expected);
        assertThat(Yatzy.yatzy(6, 6, 6, 6, 6)).isEqualTo(50);
        assertThat(Yatzy.yatzy(6, 6, 6, 6, 3)).isEqualTo(0);
    }

    @Test
    void test_1s() {
        assertThat(Yatzy.ones(1, 2, 3, 4, 5) == 1).isTrue();
        assertThat(Yatzy.ones(1, 2, 1, 4, 5)).isEqualTo(2);
        assertThat(Yatzy.ones(6, 2, 2, 4, 5)).isEqualTo(0);
        assertThat(Yatzy.ones(1, 2, 1, 1, 1)).isEqualTo(4);
    }

    @Test
    void test_2s() {
        assertThat(new Yatzy(1, 2, 3, 2, 6).twos()).isEqualTo(4);
        assertThat(new Yatzy(2, 2, 2, 2, 2).twos()).isEqualTo(10);
    }

    @Test
    void test_threes() {
        assertThat(new Yatzy(1, 2, 3, 2, 3).threes()).isEqualTo(6);
        assertThat(new Yatzy(2, 3, 3, 3, 3).threes()).isEqualTo(12);
    }

    @Test
    void fours_test()
    {
        assertThat(new Yatzy(4, 4, 4, 5, 5).fours()).isEqualTo(12);
        assertThat(new Yatzy(4, 4, 5, 5, 5).fours()).isEqualTo(8);
        assertThat(new Yatzy(4, 5, 5, 5, 5).fours()).isEqualTo(4);
    }

    @Test
    void fives() {
        assertThat(new Yatzy(4, 4, 4, 5, 5).fives()).isEqualTo(10);
        assertThat(new Yatzy(4, 4, 5, 5, 5).fives()).isEqualTo(15);
        assertThat(new Yatzy(4, 5, 5, 5, 5).fives()).isEqualTo(20);
    }

    @Test
    void sixes_test() {
        assertThat(new Yatzy(4, 4, 4, 5, 5).sixes()).isEqualTo(0);
        assertThat(new Yatzy(4, 4, 6, 5, 5).sixes()).isEqualTo(6);
        assertThat(new Yatzy(6, 5, 6, 6, 5).sixes()).isEqualTo(18);
    }

    @Test
    void one_pair() {
        assertThat(Yatzy.score_pair(3, 4, 3, 5, 6)).isEqualTo(6);
        assertThat(Yatzy.score_pair(5, 3, 3, 3, 5)).isEqualTo(10);
        assertThat(Yatzy.score_pair(5, 3, 6, 6, 5)).isEqualTo(12);
    }

    @Test
    void two_Pair() {
        assertThat(Yatzy.two_pair(3, 3, 5, 4, 5)).isEqualTo(16);
        assertThat(Yatzy.two_pair(3, 3, 5, 5, 5)).isEqualTo(16);
    }

    @Test
    void three_of_a_kind()
    {
        assertThat(Yatzy.three_of_a_kind(3, 3, 3, 4, 5)).isEqualTo(9);
        assertThat(Yatzy.three_of_a_kind(5, 3, 5, 4, 5)).isEqualTo(15);
        assertThat(Yatzy.three_of_a_kind(3, 3, 3, 3, 5)).isEqualTo(9);
    }

    @Test
    void four_of_a_knd() {
        assertThat(Yatzy.four_of_a_kind(3, 3, 3, 3, 5)).isEqualTo(12);
        assertThat(Yatzy.four_of_a_kind(5, 5, 5, 4, 5)).isEqualTo(20);
        assertThat(Yatzy.three_of_a_kind(3, 3, 3, 3, 3)).isEqualTo(9);
    }

    @Test
    void smallStraight() {
        assertThat(Yatzy.smallStraight(1, 2, 3, 4, 5)).isEqualTo(15);
        assertThat(Yatzy.smallStraight(2, 3, 4, 5, 1)).isEqualTo(15);
        assertThat(Yatzy.smallStraight(1, 2, 2, 4, 5)).isEqualTo(0);
    }

    @Test
    void largeStraight() {
        assertThat(Yatzy.largeStraight(6, 2, 3, 4, 5)).isEqualTo(20);
        assertThat(Yatzy.largeStraight(2, 3, 4, 5, 6)).isEqualTo(20);
        assertThat(Yatzy.largeStraight(1, 2, 2, 4, 5)).isEqualTo(0);
    }

    @Test
    void fullHouse() {
        assertThat(Yatzy.fullHouse(6, 2, 2, 2, 6)).isEqualTo(18);
        assertThat(Yatzy.fullHouse(2, 3, 4, 5, 6)).isEqualTo(0);
    }
}
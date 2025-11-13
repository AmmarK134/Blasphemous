package utility;

public class Constants {
    /*
     * Nested class for defining direction constants.
     */
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    /*
     * Nested class for defining player action constants and related methods.
     */
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 9;
        public static final int ATTACK_1 = 1;
        public static final int ATTACK_2 = 2;
        public static final int BLOCK = 8;
        public static final int HIT = 6;
        public static final int DIE = 4;

        /*
         * Returns the number of sprites for a given player action
         * pre player_action The player action constant.
         * post The number of sprites for the given player action.
         */
        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 7;
                case IDLE:
                case ATTACK_2:
                    return 4;
                case ATTACK_1:
                    return 5;
                case BLOCK:
                    return 1;
                case HIT:
                    return 2;
                case DIE:
                    return 6;
                default:
                    return 1;
            }
        }
    }

    /*
     * Nested class for defining skeleton action constants and related methods.
     */
    public static class SkeleConstants {
        public static final int IDLES = 2;
        public static final int ATTACK_1S = 0;
        public static final int HITS = 3;
        public static final int WALKS = 5;
        public static final int DIE_S = 1;

        /*
         * Returns the number of sprites for a given skeleton action.
         * pre skele_action The skeleton action constant.
         * post The number of sprites for the given skeleton action.
         */
        public static int GetSkeleAmount(int skele_action) {
            switch (skele_action) {
                case IDLES:
                    return 4;
                case ATTACK_1S:
                    return 8;
                case HITS:
                    return 4;
                case WALKS:
                    return 4;
                case DIE_S:
                    return 4;
                default:
                    return 1;
            }
        }
    }

    /*
     * Nested class for defining boss action constants and related methods.
     */
    public static class BossConstants {
        public static final int WALK_B = 1;
        public static final int IDLE_B = 0;
        public static final int ATTACK1_B = 3;
        public static final int ATTACK2_B = 4;

        /*
         * Returns the number of sprites for a given boss action.
         * pre boss_action The boss action constant.
         * post The number of sprites for the given boss action.
         */
        public static int GetBossAmount(int boss_action) {
            switch (boss_action) {
                case WALK_B:
                    return 7;
                case IDLE_B:
                    return 7;
                case ATTACK1_B:
                    return 10;
                case ATTACK2_B:
                    return 4;
                default:
                    return 1;
            }
        }
    }
}

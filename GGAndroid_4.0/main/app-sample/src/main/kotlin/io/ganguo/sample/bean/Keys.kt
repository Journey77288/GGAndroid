package io.ganguo.sample.bean

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/07/27
 *   @desc   : intent传参Key
 * </pre>
 */
sealed class Keys {

    object Intent {
        object Common {
            const val DATA = "data"
            const val FLAG = "flag"
        }

        object Main {

        }
    }

    object Cache {
        object Common {
            const val DATA = "data"
            const val LANGUAGE = "language"
        }
    }

    object RxBus {
        object Common {
            const val INTENT_DATA_KEY = "data"
        }

        object Main {

        }
    }

}

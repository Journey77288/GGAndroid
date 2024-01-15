package io.ganguo.utils

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/11/23
 *   @desc   : 文件类型
 * </pre>
 */




/**
 * 图片类型
 * @property value String
 * @constructor
 */
enum class ImageType(var value: String) {
	JPG("jpg"),
	PNG("png");

	companion object {
		/**
		 * 自定义[ImageType]
		 * @param format String
		 * @return ImageType
		 */
		@JvmStatic
		fun parse(format: String): ImageType {
			return values().singleOrNull {
				it.value == format
			} ?: PNG
		}
	}
}
package io.image.enums

/**
 * <pre>
 *   @author : leo
 *   @time   : 2020/10/27
 *   @desc   : 加载图片资源类型
 * </pre>
 *
 * @see [URL] 网络图片链接
 * @see [FILE] 本地文件
 * @see [FILE_PATH] 本地文件路径
 * @see [DRAWABLE_RES] 项目工程图片资源
 * @see [BITMAP] 位图
 * @see [DRAWABLE] Drawable对象
 * @see [URI] 本地文件 URI
 * @see [NO_SET_DATA] 未设置加载图片数据
 */
enum class ImageResourceType {
	URL,
	FILE,
	FILE_PATH,
	DRAWABLE_RES,
	BITMAP,
	DRAWABLE,
	URI,
	NO_SET_DATA
}

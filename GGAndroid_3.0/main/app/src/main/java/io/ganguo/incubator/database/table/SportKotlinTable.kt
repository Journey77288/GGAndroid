package io.ganguo.incubator.database.table

import io.ganguo.ggcache.database.box.IBoxTable
import io.ganguo.ggcache.database.box.IBoxTableCrud
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb

/**
 * <pre>
 *     author : leo
 *     time   : 2020/01/29
 *     desc   : 运动类型数据表
 * </pre>
 */
@Entity
class SportKotlinTable : IBoxTable {
    @Id
    var id: Long = 0

    @NameInDb("name")
    var name: String = ""

    /**
     * 默认使用伴生对象处理数据增删改查（等于java中的单例对象）
     */
    companion object : IBoxTableCrud<SportKotlinTable> {

        override val tableClass: Class<SportKotlinTable> by lazy {
            SportKotlinTable::class.java
        }
    }
}
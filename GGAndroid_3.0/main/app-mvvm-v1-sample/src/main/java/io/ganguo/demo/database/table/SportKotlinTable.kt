package io.ganguo.demo.database.table

import io.ganguo.demo.R
import io.ganguo.ggcache.database.box.IBoxTable
import io.ganguo.ggcache.database.box.IBoxTableCrud
import io.ganguo.utils.helper.ResHelper
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


    companion object : IBoxTableCrud<SportKotlinTable> {
        override val tableClass: Class<SportKotlinTable> by lazy { SportKotlinTable::class.java }

        /**
         * 插入运动测试数据
         */
        @JvmStatic
        fun insertTestSport() {
            val sports = ResHelper.getStringArray(R.array.sports)
            val tables = sports.map {
                var table = SportKotlinTable()
                table.name = it
                table
            }.toMutableList()
            insertAll(tables)
        }


    }

}
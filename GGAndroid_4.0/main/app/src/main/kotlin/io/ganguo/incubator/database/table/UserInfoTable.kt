package io.ganguo.incubator.database.table

import io.ganguo.cache.database.box.IBoxTable
import io.ganguo.cache.database.box.IBoxTableCrud
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb

/**
 * <pre>
 *     author : leo
 *     time   : 2020/07/09
 *     desc   : local UserInfo Database Table
 * </pre>
 */
@Entity
class UserInfoTable : IBoxTable {
    @Id
    @NameInDb("id")
    var id: Long = 0

    @NameInDb("name")
    var name: String = ""

    companion object : IBoxTableCrud<UserInfoTable> {
        override val tableClass: Class<UserInfoTable> by lazy {
            UserInfoTable::class.java
        }
    }
}
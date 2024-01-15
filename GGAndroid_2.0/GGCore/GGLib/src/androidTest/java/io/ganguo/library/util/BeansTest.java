package io.ganguo.library.util;

import java.io.Serializable;

import io.ganguo.library.ApplicationTest;
import io.ganguo.utils.util.Beans;

/**
 * Created by Tony on 9/30/15.
 */
public class BeansTest extends ApplicationTest implements Serializable {

    public class Bean implements Serializable {
        int id;
        String name;
    }

    public void testCopyObject() throws Exception {
        Bean bean = new Bean();
        bean.id = 1;
        bean.name = "hello";

        Bean copy = Beans.copyObject(bean);

        assertTrue(bean != copy);
        assertEquals(bean.id, copy.id);
        assertEquals(bean.name, copy.name);
    }
}

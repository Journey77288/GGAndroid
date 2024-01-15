package io.ganguo.library.util;

import org.junit.Test;

import java.io.Serializable;

import io.ganguo.utils.util.Beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Tony on 9/30/15.
 */
public class BeansTest implements Serializable {

    public class Bean implements Serializable {
        int id;
        String name;
    }

    @Test
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

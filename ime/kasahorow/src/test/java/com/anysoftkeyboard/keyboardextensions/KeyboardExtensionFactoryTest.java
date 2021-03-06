package com.anysoftkeyboard.keyboardextensions;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import com.anysoftkeyboard.AnySoftKeyboardRobolectricTestRunner;
import com.kasahorow.android.keyboard.app.R;
import com.menny.android.anysoftkeyboard.AnyApplication;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AnySoftKeyboardRobolectricTestRunner.class)
public class KeyboardExtensionFactoryTest {

    @Test
    public void testGetCurrentKeyboardExtensionBottomDefault() throws Exception {
        KeyboardExtension extension =
                AnyApplication.getBottomRowFactory(getApplicationContext()).getEnabledAddOn();
        Assert.assertNotNull(extension);
        Assert.assertEquals("c921fb90-31ed-46f4-b8a2-1322d9a62238", extension.getId());
        Assert.assertEquals(KeyboardExtension.TYPE_BOTTOM, extension.getExtensionType());
        Assert.assertEquals(
                R.xml.ext_kbd_bottom_row_regular_with_voice, extension.getKeyboardResId());
    }

    @Test
    public void testGetCurrentKeyboardExtensionBottomChanged() throws Exception {
        AnyApplication.getBottomRowFactory(getApplicationContext())
                .setAddOnEnabled("2a918967-13b3-4fd3-8368-815702dd6348", true);
        KeyboardExtension extension =
                AnyApplication.getBottomRowFactory(getApplicationContext()).getEnabledAddOn();
        Assert.assertNotNull(extension);
        Assert.assertEquals("2a918967-13b3-4fd3-8368-815702dd6348", extension.getId());
        Assert.assertEquals(KeyboardExtension.TYPE_BOTTOM, extension.getExtensionType());
        Assert.assertEquals(R.xml.ext_kbd_bottom_row_iphone, extension.getKeyboardResId());
    }

    @Test
    public void testGetCurrentKeyboardExtensionTopDefault() throws Exception {
        KeyboardExtension extension =
                AnyApplication.getTopRowFactory(getApplicationContext()).getEnabledAddOn();
        Assert.assertNotNull(extension);
        Assert.assertEquals("e60bb32b-6688-4dec-b9d0-2b04c64916c5", extension.getId());
        Assert.assertEquals(KeyboardExtension.TYPE_TOP, extension.getExtensionType());
        Assert.assertEquals(R.xml.ext_kbd_top_row_small, extension.getKeyboardResId());
    }

    @Test
    public void testGetCurrentKeyboardExtensionTopChanged() throws Exception {
        AnyApplication.getTopRowFactory(getApplicationContext())
                .setAddOnEnabled("5f1d6d76-f8f7-467c-b93f-6c8d6c4a6dfc", true);
        KeyboardExtension extension =
                AnyApplication.getTopRowFactory(getApplicationContext()).getEnabledAddOn();
        Assert.assertNotNull(extension);
        Assert.assertEquals("5f1d6d76-f8f7-467c-b93f-6c8d6c4a6dfc", extension.getId());
        Assert.assertEquals(KeyboardExtension.TYPE_TOP, extension.getExtensionType());
        Assert.assertEquals(R.xml.ext_kbd_top_row_normal, extension.getKeyboardResId());
    }

    @Test
    public void testGetCurrentKeyboardExtensionExtensionDefault() throws Exception {
        KeyboardExtension extension =
                AnyApplication.getKeyboardExtensionFactory(getApplicationContext())
                        .getEnabledAddOn();
        Assert.assertNotNull(extension);
        Assert.assertEquals("d8e1204b-6d19-4673-9b24-0ab9571681a5", extension.getId());
        Assert.assertEquals(KeyboardExtension.TYPE_EXTENSION, extension.getExtensionType());
        Assert.assertEquals(
                R.xml.ext_kbd_ext_keyboard_numbers_symbols, extension.getKeyboardResId());
    }

    @Test
    public void testGetAllAvailableExtensions() throws Exception {
        assertBasicListDetails(
                AnyApplication.getBottomRowFactory(getApplicationContext()).getAllAddOns(),
                14,
                KeyboardExtension.TYPE_BOTTOM);
        assertBasicListDetails(
                AnyApplication.getTopRowFactory(getApplicationContext()).getAllAddOns(),
                11,
                KeyboardExtension.TYPE_TOP);
        assertBasicListDetails(
                AnyApplication.getKeyboardExtensionFactory(getApplicationContext()).getAllAddOns(),
                1,
                KeyboardExtension.TYPE_EXTENSION);
    }

    private void assertBasicListDetails(
            List<KeyboardExtension> availableExtensions,
            int extensionsCount,
            @KeyboardExtension.KeyboardExtensionType int type) {
        Assert.assertNotNull(availableExtensions);
        Assert.assertEquals(extensionsCount, availableExtensions.size());
        for (KeyboardExtension extension : availableExtensions) {
            Assert.assertNotNull(extension);
            Assert.assertEquals(type, extension.getExtensionType());
        }
    }
}

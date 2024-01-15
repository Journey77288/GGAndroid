package io.ganguo.utils.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.AnyRes;
import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FractionRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.annotation.XmlRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Resources 委托方法
 * Created by Roger on 26/12/2016.
 */

public final class ResourcesDelegate {
    private static WeakReference<Context> contextWeakRef;

    private ResourcesDelegate(Context context) {
        contextWeakRef = new WeakReference<Context>(context);
    }

    public static ResourcesDelegate create(Context context) {
        return new ResourcesDelegate(context);
    }

    public static Resources getSystem() {
        return Resources.getSystem();
    }

    public CharSequence getText(@StringRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getText(id);
    }

    public CharSequence getQuantityText(@PluralsRes int id, int quantity) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getQuantityText(id, quantity);
    }

    public String getString(@StringRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getString(id);
    }

    public String getString(@StringRes int id, Object... formatArgs) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getString(id, formatArgs);
    }

    public String getQuantityString(@PluralsRes int id, int quantity, Object... formatArgs) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getQuantityString(id, quantity, formatArgs);
    }

    public String getQuantityString(@PluralsRes int id, int quantity) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getQuantityString(id, quantity);
    }

    public CharSequence getText(@StringRes int id, CharSequence def) {
        return contextWeakRef.get().getResources().getText(id, def);
    }

    public CharSequence[] getTextArray(@ArrayRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getTextArray(id);
    }

    public String[] getStringArray(@ArrayRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getStringArray(id);
    }

    public int[] getIntArray(@ArrayRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getIntArray(id);
    }

    public TypedArray obtainTypedArray(@ArrayRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().obtainTypedArray(id);
    }

    public float getDimension(@DimenRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getDimension(id);
    }

    public int getDimensionPixelOffset(@DimenRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getDimensionPixelOffset(id);
    }

    public int getDimensionPixelSize(@DimenRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getDimensionPixelSize(id);
    }

    public float getFraction(@FractionRes int id, int base, int pbase) {
        return contextWeakRef.get().getResources().getFraction(id, base, pbase);
    }

    public Drawable getDrawable(@DrawableRes int id) {
        return ContextCompat.getDrawable(contextWeakRef.get(), id);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable getDrawable(@DrawableRes int id, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getDrawable(id, theme);
    }

    @Deprecated
    public Drawable getDrawableForDensity(@DrawableRes int id, int density) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getDrawableForDensity(id, density);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Drawable getDrawableForDensity(@DrawableRes int id, int density, @Nullable Resources.Theme theme) {
        return contextWeakRef.get().getResources().getDrawableForDensity(id, density, theme);
    }

    public Movie getMovie(@RawRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getMovie(id);
    }

    public int getColor(@ColorRes int id) {
        return ContextCompat.getColor(contextWeakRef.get(), id);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public int getColor(@ColorRes int id, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getColor(id, theme);
    }

    public ColorStateList getColorStateList(@ColorRes int id) {
        return ContextCompat.getColorStateList(contextWeakRef.get(), id);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public ColorStateList getColorStateList(@ColorRes int id, @Nullable Resources.Theme theme) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getColorStateList(id, theme);
    }

    public boolean getBoolean(@BoolRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getBoolean(id);
    }

    public int getInteger(@IntegerRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getInteger(id);
    }

    public XmlResourceParser getLayout(@LayoutRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getLayout(id);
    }

    public XmlResourceParser getAnimation(@AnimRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getAnimation(id);
    }

    public XmlResourceParser getXml(@XmlRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getXml(id);
    }

    public InputStream openRawResource(@RawRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().openRawResource(id);
    }

    public InputStream openRawResource(@RawRes int id, TypedValue value) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().openRawResource(id, value);
    }

    public AssetFileDescriptor openRawResourceFd(@RawRes int id) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().openRawResourceFd(id);
    }

    public void getValue(@AnyRes int id, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        contextWeakRef.get().getResources().getValue(id, outValue, resolveRefs);
    }

    public void getValueForDensity(@AnyRes int id, int density, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        contextWeakRef.get().getResources().getValueForDensity(id, density, outValue, resolveRefs);
    }

    public void getValue(String name, TypedValue outValue, boolean resolveRefs) throws Resources.NotFoundException {
        contextWeakRef.get().getResources().getValue(name, outValue, resolveRefs);
    }

    public Resources.Theme newTheme() {
        return contextWeakRef.get().getResources().newTheme();
    }

    public TypedArray obtainAttributes(AttributeSet set, int[] attrs) {
        return contextWeakRef.get().getResources().obtainAttributes(set, attrs);
    }

    public void updateConfiguration(Configuration config, DisplayMetrics metrics) {
        contextWeakRef.get().getResources().updateConfiguration(config, metrics);
    }

    public DisplayMetrics getDisplayMetrics() {
        return contextWeakRef.get().getResources().getDisplayMetrics();
    }

    public Configuration getConfiguration() {
        return contextWeakRef.get().getResources().getConfiguration();
    }

    public int getIdentifier(String name, String defType, String defPackage) {
        return contextWeakRef.get().getResources().getIdentifier(name, defType, defPackage);
    }

    public String getResourceName(@AnyRes int resid) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getResourceName(resid);
    }

    public String getResourcePackageName(@AnyRes int resid) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getResourcePackageName(resid);
    }

    public String getResourceTypeName(@AnyRes int resid) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getResourceTypeName(resid);
    }

    public String getResourceEntryName(@AnyRes int resid) throws Resources.NotFoundException {
        return contextWeakRef.get().getResources().getResourceEntryName(resid);
    }

    public void parseBundleExtras(XmlResourceParser parser, Bundle outBundle) throws XmlPullParserException, IOException {
        contextWeakRef.get().getResources().parseBundleExtras(parser, outBundle);
    }

    public void parseBundleExtra(String tagName, AttributeSet attrs, Bundle outBundle) throws XmlPullParserException {
        contextWeakRef.get().getResources().parseBundleExtra(tagName, attrs, outBundle);
    }

    public AssetManager getAssets() {
        return contextWeakRef.get().getResources().getAssets();
    }

    public void flushLayoutCache() {
        contextWeakRef.get().getResources().flushLayoutCache();
    }

    public void finishPreloading() {
        contextWeakRef.get().getResources().finishPreloading();
    }

    public void destroy() {
        contextWeakRef.clear();
    }

    public boolean isDestroyed() {
        return contextWeakRef.get() == null;
    }
}

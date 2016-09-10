package com.wstrong.mygank.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import java.util.Map;

public class PreferencesHelper {
	private final SharedPreferences mPref;

	/**
	 * 保存在手机里面的文件名
	 */
	public  final String FILE_NAME = "share_data";

	public PreferencesHelper(Context context) {
		mPref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}


	/**
	 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
	 *
	 * @param key
	 * @param object
	 */
	public void put(String key, Object object) {
		SharedPreferences.Editor editor = mPref.edit();

		if (object instanceof String) {
			editor.putString(key, (String) object);
		} else if (object instanceof Integer) {
			editor.putInt(key, (Integer) object);
		} else if (object instanceof Boolean) {
			editor.putBoolean(key, (Boolean) object);
		} else if (object instanceof Float) {
			editor.putFloat(key, (Float) object);
		} else if (object instanceof Long) {
			editor.putLong(key, (Long) object);
		} else {
			editor.putString(key, object.toString());
		}

		editor.commit();
	}

	/**
	 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
	 *
	 * @param key
	 * @param defaultObject
	 * @return
	 */
	public  Object get( String key, Object defaultObject) {

		if (defaultObject instanceof String) {
			return mPref.getString(key, (String) defaultObject);
		} else if (defaultObject instanceof Integer) {
			return mPref.getInt(key, (Integer) defaultObject);
		} else if (defaultObject instanceof Boolean) {
			return mPref.getBoolean(key, (Boolean) defaultObject);
		} else if (defaultObject instanceof Float) {
			return mPref.getFloat(key, (Float) defaultObject);
		} else if (defaultObject instanceof Long) {
			return mPref.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	/**
	 * 移除某个key值已经对应的值
	 *
	 * @param key
	 */
	public void remove( String key) {
		SharedPreferences.Editor editor = mPref.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 清除所有数据
	 *
	 * @param context
	 */
	public void clear(Context context) {
		SharedPreferences.Editor editor = mPref.edit();
		editor.clear();
		editor.commit();
	}

	/**
	 * 查询某个key是否已经存在
	 *
	 * @param key
	 * @return
	 */
	public boolean contains( String key) {
		return mPref.contains(key);
	}

	/**
	 * 返回所有的键值对
	 *
	 * @return
	 */
	public  Map<String, ?> getAll() {
		return mPref.getAll();
	}

	/**
	 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
	 *
	 * @author zhy
	 *
	 */
/*	private static class SharedPreferencesCompat {
		private static final Method sApplyMethod = findApplyMethod();

		*//**
		 * 反射查找apply的方法
		 *
		 * @return
		 *//*
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private static Method findApplyMethod() {
			try {
				Class clz = SharedPreferences.Editor.class;
				return clz.getMethod("apply");
			} catch (NoSuchMethodException e) {
			}

			return null;
		}

		*//**
		 * 如果找到则使用apply执行，否则使用commit
		 *
		 * @param editor
		 *//*
		public static void apply(SharedPreferences.Editor editor) {
			try {
				if (sApplyMethod != null) {
					sApplyMethod.invoke(editor);
					return;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			editor.commit();
		}
	}*/

}
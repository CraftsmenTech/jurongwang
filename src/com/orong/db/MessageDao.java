package com.orong.db;

import java.util.ArrayList;

import com.orong.entity.DetailMessage;
import com.orong.entity.MessageSummary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Title: MessageDao.java
 * @Description: 站内信息的CRUD操作层
 * @author lanhaizhong
 * @date 2013年8月20日 上午10:27:18
 * @version V1.0 Copyright (c) 2013 Company,Inc. All Rights Reserved.
 * 
 */
public class MessageDao {
	private Context context;

	public MessageDao(Context context) {
		this.context = context;
	}

	/**
	 * 添加或修改一条数据
	 * 
	 * @param message
	 * @return
	 */
	public long addmessage(DetailMessage message) {
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DBConstants.Message.MESSAGEID, message.getMessage_ID());
		values.put(DBConstants.Message.TITLE, message.getTitle());
		values.put(DBConstants.Message.FLAGE, message.getRead_Flag());
		values.put(DBConstants.Message.SENDTIME, message.getSend_Date().getTime());
		values.put(DBConstants.Message.CONTENT, message.getContent());
		long rowIndex = db.replace(DBConstants.Message.TABLENAME, null, values);
		db.close();
		return rowIndex;

	}

	/**
	 * 添加或修改一条数据
	 * 
	 * @param message
	 * @return
	 */
	public long addmessageList(ArrayList<MessageSummary> list) {
		if (list == null) {
			return 0;
		} else {
			DBOpenHelper helper = new DBOpenHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			for (MessageSummary message : list) {
				ContentValues values = new ContentValues();
				values.put(DBConstants.Message.MESSAGEID, message.getMessage_ID());
				values.put(DBConstants.Message.TITLE, message.getTitle());
				values.put(DBConstants.Message.FLAGE, message.getRead_Flag());
				values.put(DBConstants.Message.SENDTIME, message.getSend_Date().getTime());
				try {
					db.insertWithOnConflict(DBConstants.Message.TABLENAME, null, values, 0);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
			db.close();
		}
		return 0;

	}

	/**
	 * 分页获取基本信息
	 * 
	 * @param limit
	 *            条目的低级行开
	 * @param number
	 *            返回的条目数
	 * @return
	 */
	public ArrayList<MessageSummary> getMessages(int limit, int number) {
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = new String[] { DBConstants.Message.MESSAGEID, DBConstants.Message.TITLE, DBConstants.Message.FLAGE, DBConstants.Message.SENDTIME };
		Cursor cursor = db.query(DBConstants.Message.TABLENAME, columns, null, null, null, null, DBConstants.Message.SENDTIME + " desc", limit + "," + number);
		ArrayList<MessageSummary> list = null;
		list = new ArrayList<MessageSummary>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				MessageSummary summary = new MessageSummary();
				summary.setMessage_ID(cursor.getString(0));
				summary.setTitle(cursor.getString(1));
				summary.setRead_Flag(cursor.getInt(2));
				summary.setSend_Date(cursor.getLong(3));
				list.add(summary);
			}
		}
		cursor.close();
		db.close();
		return list;
	}

	/**
	 * 查找一条详细信息
	 * 
	 * @param messageID
	 *            信息的id
	 * @return
	 */
	public DetailMessage getDetailMessage(String messageID) {
		DBOpenHelper helper = new DBOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String[] columns = new String[] { DBConstants.Message.MESSAGEID, DBConstants.Message.TITLE, DBConstants.Message.FLAGE, DBConstants.Message.SENDTIME,
				DBConstants.Message.CONTENT };
		Cursor cursor = db.query(DBConstants.Message.TABLENAME, columns, DBConstants.Message.MESSAGEID + " =?", new String[] { messageID }, null, null, null);
		DetailMessage message = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				message = new DetailMessage();
				message.setMessage_ID(cursor.getString(0));
				message.setTitle(cursor.getString(1));
				message.setRead_Flag(cursor.getInt(2));
				message.setSend_Date(cursor.getLong(3));
				message.setContent(cursor.getString(4));
			}
		}
		cursor.close();
		db.close();
		return message;
	}
}

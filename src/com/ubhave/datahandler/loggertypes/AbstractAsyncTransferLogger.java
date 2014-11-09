package com.ubhave.datahandler.loggertypes;

import java.util.ArrayList;

import android.Manifest;
import android.content.Context;

import com.ubhave.datahandler.config.DataStorageConfig;
import com.ubhave.datahandler.config.DataTransferConfig;
import com.ubhave.datahandler.except.DataHandlerException;
import com.ubhave.sensormanager.ESException;

public abstract class AbstractAsyncTransferLogger extends AbstractTransferLogger
{

	protected AbstractAsyncTransferLogger(Context context) throws DataHandlerException, ESException
	{
		super(context);
	}
	
	@Override
	protected ArrayList<String> getPermissions()
	{
		ArrayList<String> permissions = super.getPermissions();
		permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
		return permissions;
	}

	@Override
	protected void configureDataStorage()
	{
		super.configureDataStorage();
		try
		{
			dataManager.setConfig(DataTransferConfig.DATA_TRANSER_POLICY, DataTransferConfig.TRANSFER_PERIODICALLY);
			dataManager.setConfig(DataStorageConfig.DATA_LIFE_MILLIS, getDataLifeMillis());
			dataManager.setConfig(DataTransferConfig.TRANSFER_ALARM_INTERVAL, getTransferAlarmLengthMillis());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected abstract long getDataLifeMillis();

	protected abstract long getTransferAlarmLengthMillis();
	
	public void flush() throws DataHandlerException
	{
		dataManager.postAllStoredData();
	}
}

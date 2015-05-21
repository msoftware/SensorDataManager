package com.ubhave.datastore.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.ubhave.datahandler.except.DataHandlerException;

public abstract class FileStoreAbstractReader
{
	private FileVault fileReader;

	public FileStoreAbstractReader(final FileVault vault)
	{
		this.fileReader = vault;
	}
	
	public String readFile(final String directory, final File dataFile) throws DataHandlerException
	{
		StringBuilder result = new StringBuilder();
		synchronized (FileVault.getLock(directory))
		{
			try
			{
				InputStream stream = fileReader.openForReading(dataFile);
				BufferedReader in = new BufferedReader(new InputStreamReader(stream));
				String line;
				while ((line = in.readLine()) != null)
				{
					result.append(line);
				}
				in.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				throw new DataHandlerException(DataHandlerException.IO_EXCEPTION);
			}
		}
		return result.toString();
	}
}
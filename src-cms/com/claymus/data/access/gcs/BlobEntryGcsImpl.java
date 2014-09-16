package com.claymus.data.access.gcs;

import java.nio.ByteBuffer;
import java.util.Date;

import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;

@SuppressWarnings("serial")
public class BlobEntryGcsImpl implements BlobEntry {
	
	private final String fileName;
	private final byte[] data;
	private final String mimeType;
	private final String eTag;
	private final Date lastModified;
	
	
	public BlobEntryGcsImpl( ByteBuffer byteBuffer, GcsFileMetadata gcsFileMetadata ) {
		this.fileName = gcsFileMetadata.getFilename().getObjectName();
		this.data = byteBuffer.array();
		this.mimeType = gcsFileMetadata.getOptions().getMimeType();
		this.eTag = gcsFileMetadata.getEtag();
		this.lastModified = gcsFileMetadata.getLastModified();
	}


	@Override
	public String getName() {
		return fileName;
	}

	@Override
	public byte[] getData() {
		return data;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}
	
	@Override
	public String getETag() {
		return eTag;
	}

	@Override
	public Date getLastModified() {
		return lastModified;
	}

}

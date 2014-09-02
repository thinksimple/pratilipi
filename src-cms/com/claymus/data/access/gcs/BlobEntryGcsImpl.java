package com.claymus.data.access.gcs;

import java.nio.ByteBuffer;
import java.util.Date;

import com.claymus.data.transfer.BlobEntry;
import com.google.appengine.tools.cloudstorage.GcsFileMetadata;

public class BlobEntryGcsImpl implements BlobEntry {
	
	private ByteBuffer byteBuffer;
	private GcsFileMetadata gcsFileMetadata;
	
	
	public BlobEntryGcsImpl( ByteBuffer byteBuffer, GcsFileMetadata gcsFileMetadata ) {
		this.byteBuffer = byteBuffer;
		this.gcsFileMetadata = gcsFileMetadata;
	}


	@Override
	public String getName() {
		return gcsFileMetadata.getFilename().getObjectName();
	}

	@Override
	public byte[] getData() {
		return byteBuffer.array();
	}

	@Override
	public String getMimeType() {
		return gcsFileMetadata.getOptions().getMimeType();
	}
	
	@Override
	public String getETag() {
		return gcsFileMetadata.getEtag();
	}

	@Override
	public Date getLastModified() {
		return gcsFileMetadata.getLastModified();
	}

}

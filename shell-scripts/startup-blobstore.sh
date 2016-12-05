while true
do

	logger -t ping "_BLOBSTORE_SCRIPT_RUNNING_"
	
	# cd ~
	# sudo bash ~/test/pratilipi/shell-scripts/blobstore-backup.sh 2>&1 | grep -v "^Copying "            | logger -t blobstore_script
	
	# cd ~
	# sudo bash ~/test/pratilipi/shell-scripts/blobstore-meta.sh   2>&1 | grep -v "^Setting metadata on" | logger -t blobstore_script
	
	cd ~
	sudo bash ~/test/pratilipi/shell-scripts/blobstore-cleanup.sh   2>&1 | logger -t blobstore_script
	
	sleep 360

done

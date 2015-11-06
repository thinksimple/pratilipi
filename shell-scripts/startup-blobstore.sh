while true
do

	logger -t ping "_BLOBSTORE_SCRIPT_RUNNING_"
	
	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/blobstore-backup.sh 2>&1 | grep -v "^Copying "            | logger -t blobstore_script
	
	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/blobstore-meta.sh   2>&1 | grep -v "^Setting metadata on" | logger -t blobstore_script
	
	sleep 30

done

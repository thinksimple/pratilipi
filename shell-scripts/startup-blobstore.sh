while true
do

	logger "_BLOBSTORE_SCRIPT_RUNNING_"
	
	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/blobstore-backup.sh 2>&1 | logger
	
	sleep 30

done

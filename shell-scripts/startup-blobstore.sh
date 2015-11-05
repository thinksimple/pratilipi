while true
do

	logger "_BLOBSTORE_SCRIPT_RUNNING_"
	
	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/blobstore-backup.sh
	
	sleep 30

done

while true
do

	logger "_BLOBSTORE_BACKUP_RUNNING_"
	
	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/backup-blobstore.sh 2>&1 | logger
	
	sleep 30

done

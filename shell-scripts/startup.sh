while true
do

	date
	
	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-test.sh 2>&1 | logger
	
	cd ~/prod/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-prod.sh 2>&1 | logger

	cd ~/android/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-android.sh 2>&1 | logger
	
	cd ~/worker/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-worker.sh 2>&1 | logger

	sleep 60

done

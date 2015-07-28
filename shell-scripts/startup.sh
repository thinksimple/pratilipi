while true
do

	date
	
	cd ~/android/pratilipi
	sudo bash shell-scripts/update-android.sh 2>&1 | logger
	
	cd ~/test/pratilipi
	sudo bash shell-scripts/update-test.sh 2>&1 | logger
	
	sleep 60

done

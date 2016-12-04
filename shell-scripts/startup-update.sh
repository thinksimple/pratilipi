while true
do

	logger -t ping "_CONTINUOUS_DEPLOYMENT_RUNNING_"
	
	cd ~/prod/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-prod.sh       2>&1 | grep -v "^\[INFO\]" | grep -v "Fetching origin" | logger -t cont_dep_script

	cd ~/api/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-api.sh        2>&1 | grep -v "^\[INFO\]" | grep -v "Fetching origin" | logger -t cont_dep_script
	
	cd ~/android/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-android.sh    2>&1 | grep -v "^\[INFO\]" | grep -v "Fetching origin" | logger -t cont_dep_script
	
	cd ~/worker/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-worker.sh     2>&1 | grep -v "^\[INFO\]" | grep -v "Fetching origin" | logger -t cont_dep_script

	cd ~/test/pratilipi
	sudo bash ~/test/pratilipi/shell-scripts/update-test.sh       2>&1 | grep -v "^\[INFO\]" | grep -v "Fetching origin" | logger -t cont_dep_script
	
	sleep 60

done

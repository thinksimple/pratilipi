while true
do

	logger -t ping "_CONTINUOUS_DEPLOYMENT_RUNNING_"


	if [ -d "/home/pratilipi/test" ]; then
		cd /home/pratilipi/test/pratilipi
		sudo bash /home/pratilipi/test/pratilipi/shell-scripts/update-test.sh   2>&1 | logger -t update-test
	else
		sudo mkdir -p /home/pratilipi/test
		cd /home/pratilipi/test
		sudo git clone -b master https://github.com/Pratilipi/pratilipi.git     2>&1 | logger -t update-test
	fi

	
	sleep 60

done

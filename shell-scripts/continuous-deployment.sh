while true
do

	logger -t ping "_CONTINUOUS_DEPLOYMENT_RUNNING_"


	if [ -d "~/scripts" ]; then
		cd ~/scripts/pratilipi
		sudo bash ~/scripts/pratilipi/shell-scripts/continuous-deployment-git-merge.sh $1 2>&1 | logger -t git-merge
	else
		sudo mkdir -p ~/scripts
		cd ~/scripts
		sudo git clone -b master https://github.com/Pratilipi/pratilipi.git
	fi

	if [ -d "~/test" ]; then
		cd ~/test/pratilipi
		sudo bash ~/scripts/pratilipi/shell-scripts/update-test.sh              2>&1 | logger -t update-test
	else
		sudo mkdir -p ~/test
		cd ~/test
		sudo git clone -b master https://github.com/Pratilipi/pratilipi.git     2>&1 | logger -t update-test
	fi

	
	sleep 60

done

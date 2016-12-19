export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

scripts="/root/scripts/pratilipi/shell-scripts"


while true
do

	logger -t ping "_CONTINUOUS_DEPLOYMENT_RUNNING_"


	if [ $hour -eq 8 -a $minute -eq 0 ]; then
		reboot
	fi
	
	
	if [ $hour -eq 21 -a $minute -eq 0 ]; then
		cd ~
		gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-$(date +%d) 2>&1 | logger -t blobstore-cleanup &
	fi


	if [ -d "/root/scripts" ]; then
		cd /root/scripts/pratilipi
		bash $scripts/continuous-deployment-git-merge.sh $1                     2>&1 | logger -t git-merge
	else
		mkdir -p /root/scripts
		cd /root/scripts
		git clone -b master https://github.com/Pratilipi/pratilipi.git          2>&1 | logger -t git-merge
	fi


	if [ -d "/root/prod" ]; then
		cd /root/prod/pratilipi
		bash $scripts/continuous-deployment-update-prod.sh                      2>&1 | logger -t update-prod
	else
		mkdir -p /root/prod
		cd /root/prod
		git clone -b release-prod https://github.com/Pratilipi/pratilipi.git    2>&1 | logger -t update-prod
	fi

	
	if [ -d "/root/api" ]; then
		cd /root/api/pratilipi
		bash $scripts/continuous-deployment-update-api.sh                       2>&1 | logger -t update-api
	else
		mkdir -p /root/api
		cd /root/api
		git clone -b release-api https://github.com/Pratilipi/pratilipi.git     2>&1 | logger -t update-api
	fi


	if [ -d "/root/android" ]; then
		cd /root/android/pratilipi
		bash $scripts/continuous-deployment-update-android.sh                   2>&1 | logger -t update-android
	else
		mkdir -p /root/android
		cd /root/android
		git clone -b release-android https://github.com/Pratilipi/pratilipi.git 2>&1 | logger -t update-android
	fi


	if [ -d "/root/worker" ]; then
		cd /root/worker/pratilipi
		bash $scripts/continuous-deployment-update-worker.sh                    2>&1 | logger -t update-worker
	else
		mkdir -p /root/worker
		cd /root/worker
		git clone -b release-worker https://github.com/Pratilipi/pratilipi.git  2>&1 | logger -t update-worker
	fi


	if [ -d "/root/gamma" ]; then
		cd /root/gamma/pratilipi
		bash $scripts/continuous-deployment-update-gamma.sh                     2>&1 | logger -t update-gamma
	else
		mkdir -p /root/gamma
		cd /root/gamma
		git clone -b master https://github.com/Pratilipi/pratilipi.git          2>&1 | logger -t update-gamma
	fi

	
	sleep 60

done

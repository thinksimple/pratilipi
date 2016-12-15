export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

if [ $hour -eq 18 -a $minute -eq 20 ]
then
	
	git remote set-url origin https://antshpra:$1@github.com/Pratilipi/pratilipi.git
	
	git fetch
	git reset --hard origin/master

	
	branch=$(date +%d-%b-%H-%M-Release)


	echo "Creating new branch -> $branch ..."
	git checkout -b $branch origin/master
	git push origin $branch
	
	
	update_module () {
		echo "Syncing $1 from $2 ..."
		git branch -D $1
		git push origin --delete --force $1
		git checkout -b $1 $2
		git push origin $1
	}
	
	update_module release-prod $branch
	update_module release-api $branch
	update_module release-worker $branch
	update_module release-android $branch

fi
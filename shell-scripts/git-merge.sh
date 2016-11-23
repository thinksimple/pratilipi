promptyn () {
	while true; do
		read -p "$1 " yn
		case $yn in
			[Yy]* ) return 0;;
			[Nn]* ) return 1;;
		* ) echo "Please enter Y or N ...";;
		esac
	done
}

if promptyn "Push to prod? [Y/n]"; then
	prod=true
else
	prod=false
fi

if promptyn "Push to api? [Y/n]"; then
	api=true
else
	api=false
fi

if promptyn "Push to worker? [Y/n]"; then
	worker=true
else
	worker=false
fi

if promptyn "Push to android? [Y/n]"; then
	android=true
else
	android=false
fi

echo "Initialising ...."
git init
git remote add origin git@github.com:Pratilipi/pratilipi.git
git remote set-url origin git@github.com:Pratilipi/pratilipi.git

echo "Fetching ...."
git fetch

branch=$(date +%d-%b-%H-%M-Release)

echo "Creating New branch -> $branch ...."
git checkout -b $branch origin/master
git push origin $branch


update_module () {
	echo "Syncing $1 from $2 ...."
	git tag --delete $1
	git push --delete origin refs/tags/$1
	git branch -D $1
	git push origin -D $1
	git checkout -b $1 $2
	git push origin $1
}

if $prod; then
	update_module release-prod $branch
fi

if $api; then
	update_module release-api $branch
fi

if $worker; then
	update_module release-worker $branch
fi

if $android; then
	update_module release-android $branch
fi

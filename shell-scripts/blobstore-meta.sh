export TZ=Asia/Calcutta

hour=$(date +%H)
minute=$(date +%M)

hour=${hour#0}
minute=${minute#0}

if [ $hour -eq 0 -a $minute -eq 0 ]
then
	gsutil -m setmeta -r -h "Cache-Control:public, max-age=31536000" gs://public.pratilipi.com/*
	echo "BlobStore meta data updated."
fi
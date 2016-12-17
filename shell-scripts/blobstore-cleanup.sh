gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-09
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-10

gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-$(date +%d)

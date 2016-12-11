gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-05
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-06
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-07
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-08
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-09
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-10
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-11

gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-$(date +%d)

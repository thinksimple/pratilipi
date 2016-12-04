gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/2015-11-19
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/2016-mm-01
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/2016-mm-02
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/2016-mm-03
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/2016-mm-04
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/2016-mm-30

gsutil -m rm -r gs://backup.pratilipi.com/public.pratilipi.com

gsutil -m mv -r gs://static.pratilipi.com/author-image gs://backup.pratilipi.com/static.pratilipi.com-author-image

gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-01
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-02
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-03
gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-04

gsutil -m rm -r gs://backup.pratilipi.com/static.pratilipi.com/yyyy-mm-$(date +%d)

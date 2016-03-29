gyro = 1./200.
accel = 1. / 512.
mag = 1. / 100.
baro = 1./50.
gps = 1./4.
sonar = 1. / 10.

normalizer = 100000.

qyro_count = 0
accel_count = 0
mag_count = 0
baro_count = 0
gps_count = 0
sonar_count = 0

gyro = int(gyro * normalizer)
sonar = int(sonar * normalizer)
gps = int(gps * normalizer)
mag = int(mag * normalizer)
baro = int(baro * normalizer)
accel = int(accel * normalizer)

i=1
#dssdsdsdfffee
print 'i,qyro_count,accel_count,mag_count,baro_count,gps_count,sonar_count'
while i < 99999:
	update = False
	if  i % gyro == 0:
		qyro_count+=1
		update = True
	if  i % sonar == 0:
		sonar_count+=1
		update = True
	if  i % gps == 0:
		gps_count+=1
		update = True
	if  i % baro == 0:
		baro_count+=1
		update = True
	if  i % mag == 0:
		mag_count+=1
		update = True
	if  i % accel == 0:
		accel_count+=1
		update = True
	if update:
		print str(i)+','+str(qyro_count)+','+str(accel_count)+','+str(mag_count)+','+str(baro_count)+','+str(gps_count)+','+str(sonar_count)
	i+=1



		





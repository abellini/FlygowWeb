package br.com.flygonow.dao.impl;

import br.com.flygonow.dao.DeviceDao;
import br.com.flygonow.entities.Device;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceDaoImpl extends GenericDaoImp<Device, Long> implements DeviceDao{

}
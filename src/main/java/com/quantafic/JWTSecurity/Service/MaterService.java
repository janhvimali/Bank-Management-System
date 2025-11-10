package com.quantafic.JWTSecurity.Service;

import com.quantafic.JWTSecurity.Model.DtiMaster;
import com.quantafic.JWTSecurity.Model.FoirMaster;
import com.quantafic.JWTSecurity.Repo.DtiMasterRepository;
import com.quantafic.JWTSecurity.Repo.FoirMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MaterService {
    @Autowired
    FoirMasterRepository foirMasterRepository;

    @Autowired
    DtiMasterRepository dtiMasterRepository;

    public FoirMaster createFoirMaster( FoirMaster foirMaster){
        return foirMasterRepository.save(foirMaster);
    }

    public String updateFoirMaster(Long id , FoirMaster newFoirMaster){
        FoirMaster foirMaster = foirMasterRepository.findById(id).orElseThrow(()-> new RuntimeException("Master not found"));
        foirMaster.setLowerLimit(newFoirMaster.getLowerLimit());
        foirMaster.setUpperLimit(newFoirMaster.getUpperLimit());
        foirMaster.setRiskLevel(newFoirMaster.getRiskLevel());
        foirMaster.setSysPoints(newFoirMaster.getSysPoints());

        foirMasterRepository.save(foirMaster);

        return "updated Sucessfully";

    }

    public DtiMaster createDtiMaster(DtiMaster dtiMaster){
        return dtiMasterRepository.save(dtiMaster);
    }

    public String updateDtiMaster(Long id , DtiMaster newDtiMaster){
        DtiMaster dtiMaster = dtiMasterRepository.findById(id).orElseThrow(()-> new RuntimeException("Master not found"));
        dtiMaster.setLowerLimit(newDtiMaster.getLowerLimit());
        dtiMaster.setUpperLimit(newDtiMaster.getUpperLimit());
        dtiMaster.setRiskLevel(newDtiMaster.getRiskLevel());
        dtiMaster.setSysPoints(newDtiMaster.getSysPoints());

        return "updated Sucessfully";

    }



}

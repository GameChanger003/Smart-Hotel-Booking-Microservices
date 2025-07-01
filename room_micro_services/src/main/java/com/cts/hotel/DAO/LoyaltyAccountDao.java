//package com.cts.hotel.DAO;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//<<<<<<< HEAD
//import com.cts.hotel.Model.LoyaltyAccount;
//=======
//import com.cts.hotel.Model.Loyality;
//import com.cts.hotel.Model.Loyality;
//>>>>>>> fb0a3d7 (added loyality & Redemption)
//import com.cts.hotel.Repo.LoyaltyAccountRepository;
//
//@Repository
//public class LoyaltyAccountDao {
//
//
//	@Autowired
//	private LoyaltyAccountRepository loyaltyAccountRepository;
//
//
//
//<<<<<<< HEAD
//	public LoyaltyAccount saveLoyalty(LoyaltyAccount loyaltyAccount) {
//		return loyaltyAccountRepository.save(loyaltyAccount);
//	}
//
//	public LoyaltyAccount fetchLoyaltyById(int loyaltyId) {
//=======
//	public Loyality saveLoyalty(Loyality loyaltyAccount) {
//		return loyaltyAccountRepository.save(loyaltyAccount);
//	}
//
//	public Loyality fetchLoyaltyById(int loyaltyId) {
//>>>>>>> fb0a3d7 (added loyality & Redemption)
//
//		return loyaltyAccountRepository.findById(loyaltyId).get();
//	}
//
//<<<<<<< HEAD
//	public LoyaltyAccount deleteLoyaltyById(int loyaltyId) {
//		LoyaltyAccount loyaltyAccount=fetchLoyaltyById(loyaltyId);
//=======
//	public Loyality deleteLoyaltyById(int loyaltyId) {
//		Loyality loyaltyAccount=fetchLoyaltyById(loyaltyId);
//>>>>>>> fb0a3d7 (added loyality & Redemption)
//		loyaltyAccountRepository.delete(loyaltyAccount);
//		return loyaltyAccount;
//
//	}
//
//<<<<<<< HEAD
//	public LoyaltyAccount updateLoyalty(int oldLoyaltyId,LoyaltyAccount newLoyalty) {
//		newLoyalty.setLoyaltyId(oldLoyaltyId);
//		return saveLoyalty(newLoyalty);
//	}
//
//	public List<LoyaltyAccount> fetchAllLoyalty(){
//=======
//	public Loyality updateLoyalty(int oldLoyaltyId,Loyality newLoyalty) {
//		newLoyalty.setLoyaltyID(oldLoyaltyId);
//		return saveLoyalty(newLoyalty);
//	}
//
//	public List<Loyality> fetchAllLoyalty(){
//>>>>>>> fb0a3d7 (added loyality & Redemption)
//		return loyaltyAccountRepository.findAll();
//	}
//
//
//}

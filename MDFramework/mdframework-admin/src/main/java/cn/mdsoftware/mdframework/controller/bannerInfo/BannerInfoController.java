package cn.mdsoftware.mdframework.controller.bannerInfo;

import cn.mdsoftware.mdframework.bean.entity.bannerInfo.BannerInfoDO;
import cn.mdsoftware.mdframework.common.utils.PageUtils;
import cn.mdsoftware.mdframework.common.utils.Query;
import cn.mdsoftware.mdframework.common.utils.R;
import cn.mdsoftware.mdframework.service.bannerInfo.BannerInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;





/**
 * 
 * 
 * @author 110
 * @email 110@163.com
 * @date 2018-07-27 16:39:48
 */
@Controller
@RequestMapping("/bannerInfo")
public class BannerInfoController {

//
//	@RequestMapping("/a")
//	@ResponseBody
//	public String a(){
//		return "成功";
//	}
//
//	@RequestMapping("/b")
//	@ResponseBody
//	public String B(String name){
//		if (name.equals("123"))
//			return "已存在该用户";
//		else
//		return "可以使用";
//	}
//
//
//
//	@RequestMapping("/c")
//	@ResponseBody
//	public String Test(String name){
//
//		if (name.equals("123123"))
//			return "该账号已存在";
//		else
//		return "可以使用的账号";
//	}


	@Autowired
	private BannerInfoService bannerInfoService;
	
	@GetMapping()
	@RequiresPermissions("bannerInfo:bannerInfo")
	String BannerInfo(){
	    return "bannerInfo/bannerInfo";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("bannerInfo:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<BannerInfoDO> bannerInfoList = bannerInfoService.list(query);
		int total = bannerInfoService.count(query);
		PageUtils pageUtils = new PageUtils(bannerInfoList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")

	//@RequiresPermissions("blog:bComments")
	String add(){
	    return "bannerInfo/add";
	}

	@GetMapping("/edit/{id}")
	//@RequiresPermissions("blog:bComments")
	String edit(Model model, @PathVariable("id") Integer id){
		BannerInfoDO bannerInfo = bannerInfoService.get(id);
		model.addAttribute("bannerInfo", bannerInfo);
	    return "bannerInfo/edit";
	}
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("bannerInfo:info")
	public R info(@PathVariable("id") Integer id){
		BannerInfoDO bannerInfo = bannerInfoService.get(id);
		return R.ok().put("bannerInfo", bannerInfo);
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("bannerInfo:save")
	public R save( BannerInfoDO bannerInfo){
		if(bannerInfoService.save(bannerInfo)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
    @ResponseBody
	@RequiresPermissions("bannerInfo:update")
	public R update(BannerInfoDO bannerInfo){
		bannerInfoService.update(bannerInfo);
		
		return R.ok();
	}
	
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("bannerInfo:remove")
	public R remove( Integer id){
		if(bannerInfoService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("bannerInfo:remove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		bannerInfoService.batchRemove(ids);
		
		return R.ok();
	}


}

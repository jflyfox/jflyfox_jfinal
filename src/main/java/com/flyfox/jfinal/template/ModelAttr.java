package com.flyfox.jfinal.template;

public class ModelAttr {

	/**
	 * 字段key
	 */
	private String key;
	/**
	 * 字段名称
	 */
	private String name;
	/**
	 * 编辑类型
	 */
	private FormType formType = FormType.INPUT;
	/**
	 * Input类型
	 */
	private InputType inputType = InputType.TEXT;
	/**
	 * 编辑数据
	 */
	private String formTypeData = "";
	/**
	 * 验证方式
	 */
	private String formTypeVaild = "";

	/**
	 * 数据展示
	 * 
	 * @see 学以致用，不嫌麻烦~！~
	 * 
	 * @see 8位，前四位保留;后三位，
	 * @see 查询,展示列表，添加列表，编辑列表，查看列表
	 * @see 1表示展示，0表示隐藏
	 */
	private byte operate;

	public ModelAttr() {
		operate = (byte) 0xf; // 默认不在查询里
	}

	public boolean isSearch() {
		return (operate >> 4 & 0x1) == 1;
	}

	public boolean isList() {
		return (operate >> 3 & 0x1) == 1;
	}

	public boolean isAdd() {
		return (operate >> 2 & 0x1) == 1;
	}

	public boolean isEdit() {
		return (operate >> 1 & 0x1) == 1;
	}

	public boolean isView() {
		return (operate >> 0 & 0x1) == 1;
	}

	public ModelAttr addSearch() {
		operate = (byte) (operate | 0x10); // 10000
		return this;
	}

	public ModelAttr addList() {
		operate = (byte) (operate | 0x8); // 1000
		return this;
	}

	public ModelAttr addAdd() {
		operate = (byte) (operate | 0x4); // 100
		return this;
	}

	public ModelAttr addEdit() {
		operate = (byte) (operate | 0x2); // 10
		return this;
	}

	public ModelAttr addView() {
		operate = (byte) (operate | 0x1); // 1
		return this;
	}

	public ModelAttr removeSearch() {
		operate = (byte) (operate & 0xef); // 11101111
		return this;
	}

	public ModelAttr removeList() {
		operate = (byte) (operate & 0xf7); // 11110111
		return this;
	}

	public ModelAttr removeAdd() {
		operate = (byte) (operate & 0xfb); // 11111011
		return this;
	}

	public ModelAttr removeEdit() {
		operate = (byte) (operate & 0xfd); // 11111101
		return this;
	}

	public ModelAttr removeView() {
		operate = (byte) (operate & 0xfe); // 11111110
		return this;
	}

	public String getKey() {
		return key;
	}

	public ModelAttr setKey(String key) {
		this.key = key;
		return this;
	}

	public String getName() {
		return name;
	}

	public ModelAttr setName(String name) {
		this.name = name;
		return this;
	}

	public byte getOperate() {
		return operate;
	}

	public ModelAttr setOperate(byte operate) {
		this.operate = operate;
		return this;
	}

	public FormType getFormType() {
		return formType;
	}

	public ModelAttr setFormType(FormType formType) {
		this.formType = formType;
		return this;
	}

	public String getFormTypeData() {
		return formTypeData;
	}

	public ModelAttr setFormTypeData(String formTypeData) {
		this.formTypeData = formTypeData;
		return this;
	}

	public String getFormTypeVaild() {
		return formTypeVaild;
	}

	public ModelAttr setFormTypeVaild(String formTypeVaild) {
		this.formTypeVaild = formTypeVaild;
		return this;
	}

	public InputType getInputType() {
		return inputType;
	}

	public ModelAttr setInputType(InputType inputType) {
		this.inputType = inputType;
		return this;
	}

}
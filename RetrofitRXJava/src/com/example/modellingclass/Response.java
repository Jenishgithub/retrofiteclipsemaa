package com.example.modellingclass;

import java.util.List;

public class Response {
	public List<String> destination_addresses;
	public List<String> origin_addresses;
	public List<Rows> rows;
	public String status;

	public String getStatus() {
		return status;
	}

	public List<Rows> getRows() {
		return rows;
	}

	public List<String> getOrigin_addresses() {
		return origin_addresses;
	}

	public List<String> getDestination_addresses() {
		return destination_addresses;
	}

}

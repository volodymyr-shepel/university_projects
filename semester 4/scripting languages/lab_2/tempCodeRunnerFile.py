    @property
    def path(self):
        return self._path if self.if_exist else f"!{self._path}"
    @property
    def result_code(self):
        return self._result_code
    
    @property
    def number_of_bytes(self):
        return self._number_of_bytes
    
    @property
    def processing_time(self):
        return self._processing_time
    
    @property
    def if_exist(self):
        return self._if_exist
    
import { TestBed, inject } from '@angular/core/testing';

import { ClientattendanceService } from './clientattendance.service';

describe('ClientattendanceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ClientattendanceService]
    });
  });

  it('should be created', inject([ClientattendanceService], (service: ClientattendanceService) => {
    expect(service).toBeTruthy();
  }));
});

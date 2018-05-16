import { TestBed, inject } from '@angular/core/testing';

import { ClientattendancenoteService } from './clientattendancenote.service';

describe('ClientattendancenoteService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ClientattendancenoteService]
    });
  });

  it('should be created', inject([ClientattendancenoteService], (service: ClientattendancenoteService) => {
    expect(service).toBeTruthy();
  }));
});

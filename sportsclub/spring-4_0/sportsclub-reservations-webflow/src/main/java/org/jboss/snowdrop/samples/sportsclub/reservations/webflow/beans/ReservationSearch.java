package org.jboss.snowdrop.samples.sportsclub.reservations.webflow.beans;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.service.ReservationService;

import javax.faces.context.FacesContext;
import java.util.*;

/**
 * @author <a href="mailto:lvlcek@redhat.com">Lukas Vlcek</a>
 */
public class ReservationSearch extends AbstractExtendedDataModelHelper
{
   private ReservationService reservationService;
   private ReservationSearchOptions reservationSearchOptions;

   private Map<Long, Reservation> reservationsMap = new HashMap<Long, Reservation>();

   private ReservationTableState tableState;

   private AccountFilter accountFilter;
   private EquipmentFilter equipmentFilter;
   private Locale locale = Locale.getDefault();
   private Reservation reservation;

   private boolean editing;

   public ReservationSearch()
   {
      super();
   }

   public ReservationService getReservationService()
   {
      return reservationService;
   }

   public void setReservationService(ReservationService reservationService)
   {
      this.reservationService = reservationService;
   }

   @Override
   public Map<Long, ? extends Object> getDomainObjectMap()
   {
      return reservationsMap;
   }

   @Override
   public Long getCurrentRowCount()
   {
      return reservationService.countReservationsForRange(
            reservationSearchOptions.getFromDate(),
            reservationSearchOptions.getToDate(),
            reservationSearchOptions.getSelectedEquipmentTypes());
   }

   @Override
   public void walk(FacesContext facesContext, DataVisitor dataVisitor, Range range, Object argument)
   {
      int firstResult = ((SequenceRange) range).getFirstRow();
      int maxResults = ((SequenceRange) range).getRows();
      List<Reservation> list = reservationService.getReservations(
            reservationSearchOptions.getFromDate(),
            reservationSearchOptions.getToDate(), firstResult, maxResults,
            reservationSearchOptions.getSelectedEquipmentTypes());
      reservationsMap = new HashMap<Long, Reservation>();
      for (Reservation row : list)
      {
         Long id = row.getId();
         reservationsMap.put(id, row);
         dataVisitor.process(facesContext, id, argument);
      }
   }

   public void reservationSelected()
   {
      if (getSelection() != null && getSelection().size() > 0)
      {
         reservation = reservationsMap.get(getSelectedKey());
      } else
      {
         reservation = null;
         editing = false;
      }
      cleanFilters();
   }

   private void cleanFilters()
   {
      accountFilter.clearSelection();
      equipmentFilter.clearSelection();
   }

   public ReservationTableState getTableState()
   {
      return tableState;
   }

   public void setTableState(ReservationTableState tableState)
   {
      this.tableState = tableState;
   }

   public ReservationSearchOptions getReservationSearchOptions()
   {
      return reservationSearchOptions;
   }

   public void setReservationSearchOptions(ReservationSearchOptions reservationSearchOptions)
   {
      this.reservationSearchOptions = reservationSearchOptions;
   }

   private Long getSelectedKey()
   {
      if (getSelection() == null || getSelection().size() == 0)
         return null;
      else
         return ((Long) getSelection().iterator().next());
   }

   public void deleteReservation()
   {
      if (reservation != null)
      {
         reservationService.delete(reservation);
         reservation = null;
      }
     clearSelection();
      cleanFilters();
      resetCurrentRowCount();
      editing = false;
   }

   public void saveCurrent()
   {
      if (reservation != null)
      {
         reservationService.updateReservation(reservation);
         reservation = null;
      }
      clearSelection();
      cleanFilters();
      editing = false;
   }

   public void updateSelectedAccount()
   {
      if (accountFilter.getSelection() != null && accountFilter.getSelection().size() > 0)
      {
         Account account = accountFilter.getSelectedAccount();
         reservation.setAccount(account);
      } else
      {
         reservation.setAccount(reservationsMap.get(getSelectedKey()).getAccount());
      }
   }



   public void updateSelectedEquipment()
   {
      if (equipmentFilter.getSelection() != null && equipmentFilter.getSelection().size() > 0)
      {
         Equipment equipment = equipmentFilter.getReservation().getEquipment();
         reservation.setEquipment(equipment);
      } else
      {
         reservation.setEquipment(reservationsMap.get(getSelectedKey()).getEquipment());
      }
   }

   public void setEditing(boolean editing)
   {
      this.editing = editing;
   }

   public boolean isEditing()
   {
      return editing;
   }

   public AccountFilter getAccountFilter()
   {
      return accountFilter;
   }

   public void setAccountFilter(AccountFilter accountFilter)
   {
      this.accountFilter = accountFilter;
   }

   public void refreshCount()
   {
      resetCurrentRowCount();
      getRowCount();
      setCurrentPage(1);
   }

   public EquipmentFilter getEquipmentFilter()
   {
      return equipmentFilter;
   }

   public void setEquipmentFilter(EquipmentFilter equipmentFilter)
   {
      this.equipmentFilter = equipmentFilter;
   }

   public Locale getLocale()
   {
      return locale;
   }

   public void setLocale(Locale locale)
   {
      this.locale = locale;
   }

   public Reservation getReservation()
   {
      return reservation;
   }
}
